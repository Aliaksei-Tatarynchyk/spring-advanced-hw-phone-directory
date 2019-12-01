function downloadPDF(url, fileName) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", url, true);
    xhr.responseType = 'blob';
    xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
    xhr.setRequestHeader("Accept", "application/pdf");
    xhr.onload = function (e) {
        if (this.status == 200) {
            // Create a new Blob object using the response data of the onload object
            var blob = new Blob([this.response], {type: 'application/pdf'});
            // Create a link element, hide it, direct it towards the blob, and then 'click' it programmatically
            let a = document.createElement("a");
            a.style = "display: none";
            document.body.appendChild(a);
            // Create a DOMString representing the blob and point the link element towards it
            let url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = fileName;
            // programmatically click the link to trigger the download
            a.click();
            // release the reference to the file by revoking the Object URL
            window.URL.revokeObjectURL(url);
        } else {
            // deal with your error state here
            alert(e);
        }
    };
    xhr.send();
}