function increment(container, displayCartItem, taxRate) {
    let counter = container.getElementsByClassName("counting")[0];
    let totalCounter = document.getElementById("total-items-count");

    $.ajax({
        contentType: "application/json",
        data: JSON.stringify({ "quantity": parseInt(counter.innerText) + 1 }),
        dataType: "text", // because response is not json type, so ajax will try to parse it to json and fail
        method: "PATCH",
        url: `/cart/items/${displayCartItem.cartItem.id}`,
        success: function() {
            counter.innerText = parseInt(counter.innerText) + 1;
            updateTotalItemCount(1);
            updateOrderSummary(parseFloat(displayCartItem.item.price), taxRate);
        },
        error: function(resp, error) {
            console.log(`operation failed, status code: ${resp.status}, error: ${error}`);
        }
    });
}

function decrement(container, displayCartItem, taxRate) {
    let counter = container.getElementsByClassName("counting")[0];
    let totalCounter = document.getElementById("total-items-count");

    // we don't allow customer to reduce quantity of any cart item down to zero, so 1 is minimum quantity a customer can buy
    if (parseInt(counter.innerText) > 1) {
        $.ajax({
            contentType: "application/json",
            data: JSON.stringify({ "quantity": parseInt(counter.innerText) - 1 }),
            dataType: "text", // because response is not json type, so ajax will try to parse it to json and fail
            method: "PATCH",
            url: `/cart/items/${displayCartItem.cartItem.id}`,
            success: function() {
                counter.innerText = parseInt(counter.innerText) - 1;
                updateTotalItemCount(-1);
                updateOrderSummary(parseFloat(displayCartItem.item.price) * -1, taxRate);
            },
            error: function(resp, error) {
                console.log(`operation failed, status code: ${resp.status}, error: ${error}`);
            }
        });
    }
}

function removeItem(displayCartItem, taxRate){
    let item = document.getElementById(`cart-item-${displayCartItem.cartItem.id}`);
    let counter = item.getElementsByClassName("counting")[0];
    let totalCounter = document.getElementById("total-items-count");

    $.ajax({
        method: "DELETE",
        url: `/cart/items/${displayCartItem.cartItem.id}`,
        error: function(resp, error) {
            console.log(`operation failed, status code: ${resp.status}, error: ${error}`);
        }
    });
    item.remove();
    updateTotalItemCount(parseInt(counter.innerText) * -1);
    updateOrderSummary(parseInt(counter.innerText) * parseFloat(displayCartItem.item.price) * -1, taxRate);
}

function updateTotalItemCount(itemChange) {
    let totalCounter = document.getElementById("total-items-count");
    totalCounter.innerText = String(parseInt(totalCounter.innerText) + parseInt(itemChange));
}
function updateOrderSummary(pretaxPriceChange, taxRate) {
    let pretaxSpan = document.getElementById("order-summary-pretax-total");
    let pretax = parseFloat(pretaxSpan.innerText);
    let newPretax = pretax + parseFloat(pretaxPriceChange);
    pretaxSpan.innerText = newPretax.toFixed(2);

    let taxSpan = document.getElementById("order-summary-tax");
    let newTax = newPretax * parseFloat(taxRate);
    taxSpan.innerText = newTax.toFixed(2);

    let taxedTotalSpan = document.getElementById("order-summary-taxed-total");
    let newTaxedTotal = newPretax + newTax;
    taxedTotalSpan.innerText = newTaxedTotal.toFixed(2);
}

function checkout() {
    let customerId = window.sessionStorage.getItem("customerId");
    if (customerId == null) customerId = 111;
    window.location.href=`/checkout?customerId=${customerId}`
}