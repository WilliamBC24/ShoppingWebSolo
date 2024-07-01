document.addEventListener('DOMContentLoaded', () => {
    const remover = document.querySelector('.promote1')
    alertMe = () => {
        alert('abc')
    }
    remove = () => {
        remover.parentNode.removeChild(remover)
    }
})