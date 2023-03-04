let data = 1;

document.getElementById("counting").innerText = data;

function increment(container) {
    let counter = container.getElementsByClassName("counting")[0];
    counter.innerText = parseInt(counter.innerText) + 1;
}

function decrement(container) {
    let counter = container.getElementsByClassName("counting")[0];
    if (parseInt(counter.innerText) > 0) {
        counter.innerText = parseInt(counter.innerText) - 1;
    }
}

function removeItem(){
    let item = document.getElementById('item1');
    item.remove();
}