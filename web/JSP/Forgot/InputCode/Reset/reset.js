const password=document.querySelector('.password');
const confirm=document.querySelector('.confirm');
const button=document.querySelector('.button');

const validatePassword = (password) => {
    const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,100}$/;
    return regex.test(password)
}
button.addEventListener('click',(ev)=>{
    const pValue=password.value;
    const cValue=confirm.value;
    if(!cValue||!pValue){
        alert('Please fill the fields');
        ev.preventDefault()
    }
    else if(cValue!=pValue){
        alert('Passwords do not match');
        ev.preventDefault()
    }
    else if(!validatePassword(pValue)){
        alert('Passwords must be at least 8 characters long, have at least 1 letter, 1 number and 1 special character');
        ev.preventDefault()
    }
})
