function isDataValid() {
    let form = document.getElementsByTagName('form')[0];
    if (isDurationValid() === true && isListPriceValid() === true && isSalePriceValid() === true) {
        return true;
    }
    return false;
}

function isDurationValid() {
    if (isNaN(Number(document.querySelector('#duration').value.trim())) === false) {
        if (document.querySelector('#duration').value.trim() !== '') {
            let duration = Number(document.querySelector('#duration').value.trim());
            if (Number.isInteger(duration) == false || duration < 1) {
                document.querySelector('.text-danger.duration').style.display = 'inline';
                return false;
            } else {
                document.querySelector('.text-danger.duration').style.display = 'none';
                return true;
            }
        } else {
            document.querySelector('.text-danger.duration').style.display = 'none';
            return true;
        }
    } else {
        document.querySelector('.text-danger.duration').style.display = 'inline';
        return false;
    }
}

function isListPriceValid() {
    if (isNaN(Number(document.querySelector('#listPrice').value.trim())) === false) {
        if (document.querySelector('#listPrice').value.trim() !== '') {
            let listPrice = Number(document.querySelector('#listPrice').value.trim());
            if (listPrice < 0) {
                document.querySelector('.text-danger.list-price').style.display = 'inline';
                return false;
            } else {
                document.querySelector('.text-danger.list-price').style.display = 'none';
                return true;
            }
        } else {
            document.querySelector('.text-danger.list-price').style.display = 'inline';
            return false;
        }
    } else {
        document.querySelector('.text-danger.list-price').style.display = 'inline';
        return false;
    }
}

function isSalePriceValid() {
    if (isNaN(Number(document.querySelector('#salePrice').value.trim())) === false) {
        if (document.querySelector('#salePrice').value.trim() !== '') {
            let salePrice = Number(document.querySelector('#salePrice').value.trim());
            if (salePrice < 0) {
                document.querySelector('.text-danger.sale-price').style.display = 'inline';
                return false;
            } else {
                document.querySelector('.text-danger.sale-price').style.display = 'none';
                return true;
            }
        } else {
            document.querySelector('.text-danger.sale-price').style.display = 'inline';
            return false;
        }
    } else {
        document.querySelector('.text-danger.sale-price').style.display = 'inline';
        return false;
    }
}