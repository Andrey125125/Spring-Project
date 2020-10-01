
function isAppropriate() {
    if (document.getElementById('passwordId').value ==
        document.getElementById('passwordAgainId').value) {
        document.getElementById('id_btnReg').disabled = false;
    } else {
        document.getElementById('id_btnReg').disabled = true;
    }
}