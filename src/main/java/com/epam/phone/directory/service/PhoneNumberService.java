package com.epam.phone.directory.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.epam.phone.directory.model.AmountOfMoney;
import com.epam.phone.directory.model.db.PhoneCompany;
import com.epam.phone.directory.model.db.PhoneNumber;
import com.epam.phone.directory.model.exception.GenericFundsException;
import com.epam.phone.directory.repository.PhoneCompanyRepository;
import com.epam.phone.directory.repository.PhoneNumberRepository;
import com.epam.phone.directory.repository.UserAccountRepository;

@Service
public class PhoneNumberService {

    public static final AmountOfMoney TARIFF_FOR_CHANGING_MOBILE_OPERATOR = new AmountOfMoney(5);

    final PhoneNumberRepository phoneNumberRepository;
    final PhoneCompanyRepository phoneCompanyRepository;
    final UserAccountRepository userAccountRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository, PhoneCompanyRepository phoneCompanyRepository, UserAccountRepository userAccountRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneCompanyRepository = phoneCompanyRepository;
        this.userAccountRepository = userAccountRepository;
    }

    /**
     * There are 2 cases how this method can be tested from UI:
     * - change operator to itself
     * - change operator when user has less than {@link com.epam.phone.directory.model.db.UserAccount#DEFAULT_ACCOUNT_CURRENCY 5$}
     * on the account balance
     *
     * The order of operations was "designed" to make it evident that all operations are rolled back in case of failure,
     * e.g. phone mobile operator should not be changed if there is not enough money on the phone number account balance.
     *
     * @param phoneNumberId
     * @param newMobileOperatorId
     * @throws GenericFundsException
     * @throws IllegalArgumentException
     */
    @Transactional
    public void changeMobileOperator(Long phoneNumberId, Long newMobileOperatorId) throws GenericFundsException, IllegalArgumentException {
        PhoneNumber phoneNumber = phoneNumberRepository.findById(phoneNumberId)
                .orElseThrow(() -> new IllegalArgumentException("Phone number with id " + phoneNumberId + " is not found in repository"));
        PhoneCompany newMobileOperator = phoneCompanyRepository.findById(newMobileOperatorId)
                .orElseThrow(() -> new IllegalArgumentException("Mobile operator with id " + phoneNumberId + " is not found in repository"));

        PhoneCompany currentMobileOperator = phoneNumber.getMobileOperator();

        currentMobileOperator.getUserAccount().increaseBalance(TARIFF_FOR_CHANGING_MOBILE_OPERATOR);
        userAccountRepository.save(currentMobileOperator.getUserAccount());

        if (currentMobileOperator == newMobileOperator) {
            throw new IllegalArgumentException("Changing of mobile operator is cancelled because the new operator is the same as current");
        }

        phoneNumber.setMobileOperator(newMobileOperator);
        phoneNumberRepository.save(phoneNumber);

        phoneNumber.getUserAccount().decreaseBalance(TARIFF_FOR_CHANGING_MOBILE_OPERATOR);
        userAccountRepository.save(phoneNumber.getUserAccount());
    }
}
