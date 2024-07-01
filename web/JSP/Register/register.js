
const signupbutton = document.querySelector('#signupbutton');
const signinbutton = document.querySelector('#signinbutton');
const container = document.querySelector('.container');

const regUser = document.querySelector('.reguser');
const regMail = document.querySelector('.regmail');
const regPass = document.querySelector('.regpass');
const regFirst = document.querySelector('.regfirst');
const regLast = document.querySelector('.reglast');
const regGen = document.querySelector('.reggen');
const regPhone = document.querySelector('.regphone');
const regCont = document.querySelector('.regcont');
const regBack = document.querySelector('.regback');
const regSub = document.querySelector('.regsub');
const regSocial1 = document.querySelector('.regsocial1');
const regSocial2 = document.querySelector('.regsocial2');
const regError = document.querySelector('.error');
const logUser = document.querySelector('.logUser');
const logPass = document.querySelector('.logPass');

const remember = document.querySelector('.remember');

const logButton = document.querySelector('.logButton');
logButton.addEventListener('click', (ev) => {
    const logUserV = document.querySelector('.logUser').value;
    const logPassV = document.querySelector('.logPass').value;
    if (!logUserV || !logPassV) {
        alert('Please fill all the fields');
        ev.preventDefault();
    }
})

const generateToken = () => {
    return Array.from(window.crypto.getRandomValues(new Uint32Array(16)), dec => dec.toString(16).padStart(8, '0')).join('');
}
const setCookie = (name, value, days) => {
    const d = new Date();
    d.setTime(d.getTime() + (days * 24 * 60 * 60 * 1000));
    const expires = "expires=" + d.toUTCString();
    document.cookie = `${name}=${value};${expires};path=/`;
}
const generateAndStoreToken = (cookieName, expirationDays) => {
    const token = generateToken();
    setCookie(cookieName, token, expirationDays);
}
const rememberMe=()=>{
    if(remember.checked){
        generateAndStoreToken(logUser.value,365)
    }
}

const toggleFade = () => {
    [regUser, regMail, regPass, regFirst, regLast, regGen, regPhone, regCont, regBack, regSub, regSocial1, regSocial2, regError].forEach(el => {
        if (el) {
            el.classList.toggle('fade');
        }
    });
};
const defaultState = () => {
    [regUser, regMail, regPass, regCont, regSocial1, regSocial2, regError].forEach(el => {
        if (el) {
            el.classList.remove('fade');
        }
    });
    [regFirst, regLast, regGen, regPhone, regBack, regSub].forEach(el => {
        if (el) {
            el.classList.add('fade');
        }
    });
}

signupbutton.addEventListener('click', () => {
    container.classList.add('flip');
    setTimeout(() => {
        logUser.value = "";
        logPass.value = "";
    }, 500);
});
signinbutton.addEventListener('click', () => {
    container.classList.remove('flip');
    setTimeout(() => {
        [regUser, regMail, regPass, regFirst, regLast, regPhone].forEach(el => {
            const elI = el.querySelector("input");
            if (elI && elI.value) {
                elI.value = "";
            }
        })
        regGen.querySelector('select').selectedIndex = 0;
        defaultState();
    }, 500);
});


regBack.addEventListener('click', () => {
    regFirst.querySelector('input').value = "";
    regLast.querySelector('input').value = "";
    regPhone.querySelector('input').value = "";
    toggleFade();
});

const validateEmail = (email) => {
    const regex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    return regex.test(email);
}
const validatePassword = (password) => {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,100}$/;
    return regex.test(password)
}
regCont.addEventListener('click', (ev) => {
    const username = regUser.querySelector('input').value;
    const email = regMail.querySelector('input').value;
    const password = regPass.querySelector('input').value;

    if (!username || !password || !email) {
        alert('Please fill in all required fields.');
        ev.preventDefault()
    } else if (!validateEmail(email)) {
        alert('Please use the correct format for email (ex@domain.com)');
        ev.preventDefault()
    } else if (!validatePassword(password)) {
        alert('Password must be 8-100 characters long, contains at least a letter, a number and a special character')
        ev.preventDefault()
    } else {
        toggleFade()
    }
});
regSub.addEventListener('click', (ev) => {
    const first = regFirst.querySelector('input').value;
    const last = regLast.querySelector('input').value;
    const phone = regPhone.querySelector('input').value;
    const phoneRegex = /^\d{7,12}$/;
    if (!first || !last || !phone) {
        alert('Please fill in all the fields');
        ev.preventDefault();
    }
    else if (!phoneRegex.test(phone)) {
        alert('Please input the right phone number format');
        ev.preventDefault();
    }
});
