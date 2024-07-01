const email=document.querySelector('.input');
const button=document.querySelector('.button');

function validateEmail(email) {
    const regex = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    return regex.test(email);
}
button.addEventListener('click',(ev)=>{
    const eValue=email.querySelector('input').value;
    if(!validateEmail(eValue)){
        alert('Please use the correct format for email (ex@domain.com)');
       ev.preventDefault()
    }
})