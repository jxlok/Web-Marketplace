
function analytics_tab(){
    let analytics = document.getElementById('data-analytics');
    let order_history = document.getElementById("order-history")
    let items = document.getElementById('items-for-sale');

    analytics.removeAttribute("hidden");
    order_history.removeAttribute("hidden")
    items.setAttribute("hidden", "hidden");
}

function items_tab(){
    let analytics = document.getElementById('data-analytics');
    let order_history = document.getElementById("order-history")
    let items = document.getElementById('items-for-sale');

    items.removeAttribute("hidden");
    analytics.setAttribute("hidden", "hidden");
    order_history.setAttribute("hidden", "hidden");

}