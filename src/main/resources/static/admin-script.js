
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

function addItem(){
    // let items = document.getElementById("items")
    //
    // items.innerHTML =
    //     "<form id='addItem-form'>" +
    //     "   <div class=\"item\">" +
    //     "       <span class=\"item-name\"><input type='text' placeholder='Enter item title'></span>\n" +
    //     "       <span class=\"item-desc\"><textarea name='change-description' placeholder='Enter Description' cols='40' rows='6'></textarea></span>\n" +
    //     "       <span class=\"item-price\">$<input type='text'></span>\n" +
    //     "       <span class=\"item-quantity\"><input type='text'></span>\n" +
    //     "       <span id='confirm-change' class=\"item-edit-icon\">/tick</span>\n" +
    //     "       <span id='delete-change' class=\"item-delete-icon\">X</span>" +
    //     "   </div>" +
    //     "</form>"
    //     + items.innerHTML;

    let form = document.getElementById("addItem-form");
    form.removeAttribute("hidden")

    let addItemButton = document.getElementById("addItem");
    addItemButton.removeAttribute("onclick");
}

function addNewItem() {

    let form = document.getElementById("addItem-form");
    let items = document.getElementById("items");
    let desc = document.getElementById("desc-change");
    items.innerHTML =
        "   <div class=\"item\">" +
        "       <span class=\"item-name\"><strong>"+form.name.value+"</strong></span>\n" +
        "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc.value+"</p></span>\n" +
        "       <span class=\"item-price\">$"+ form.price.value +"</span>\n" +
        "       <span class=\"item-quantity\">"+ form.quant.value +"</span>\n" +
        "       <span class=\"item-edit-icon\">!</span>\n" +
        "       <span class=\"item-delete-icon\">hide</span>" +
        "   </div>"
        + items.innerHTML;

    resetAddItem()
}

function resetAddItem(){

    let addform = document.getElementById("addItem-form");
    addform.reset();
    addform.setAttribute("hidden", "hidden");

    let button = document.getElementById("addItem");
    button.setAttribute("onclick", "addItem()")
}

