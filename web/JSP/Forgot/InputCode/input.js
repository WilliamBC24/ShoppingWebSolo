const code=document.querySelector('.code');
const button=document.querySelector('.button');
const time=document.querySelector('.countdown');
const link=document.querySelector('.link');
let interval;
let sec=60;
time.textContent = `(00:${String(sec).padStart(2, '0')})`;
button.addEventListener('click',(ev)=>{
    const cValue=code.value;
    if(!cValue){
        alert('Please input code');
       ev.preventDefault()
    }
})
link.addEventListener('click', (ev) => {
    if (sec > 0) {
        ev.preventDefault();
    } else {
        sec = 60;
        time.textContent = `(00:${String(sec).padStart(2, '0')})`;
        clearInterval(interval)
        startCountdown();
    }
});

function startCountdown() {
    interval = setInterval(function () {
        if (sec > 0) {
            sec--;
            time.textContent = `(00:${String(sec).padStart(2, '0')})`;
        } else {
            clearInterval(interval);
        }
    }, 1000);
}
startCountdown()