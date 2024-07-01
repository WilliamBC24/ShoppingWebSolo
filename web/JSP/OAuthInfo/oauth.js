const button = document.querySelector('.button');

button.addEventListener('click', (ev) => {
    const username=document.querySelector('.username').value
    const phone = document.querySelector('.phone').value
    const first = document.querySelector('.first').value
    const last = document.querySelector('.last').value
    const phoneRegex = /^\d{7,12}$/;
    if (!first || !last || !phone||!username) {
        alert('Please fill in all the fields');
        ev.preventDefault();
    }
    else if (!phoneRegex.test(phone)) {
        alert('Please input the right phone number format');
        ev.preventDefault();
    }
});