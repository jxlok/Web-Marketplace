
window.onload = function(){
    activateEditButtons()
}

let listener = function editMode(){
    editItem(this.parentNode);
}

function activateEditButtons(){
    let items = document.getElementsByClassName("item-edit-icon")
    for(var i=0;i<items.length;i++){
        items[i].addEventListener("click", listener);
    }
}

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
    let form = document.getElementById("addItem-form");
    form.removeAttribute("hidden")

    let addItemButton = document.getElementById("addItem");
    addItemButton.removeAttribute("onclick");

    deactivateEditButtons()
}

function addNewItem() {

    let form = document.getElementById("addItem-form");
    let items = document.getElementById("items");
    let desc = document.getElementById("desc-new");
    items.innerHTML =
        "   <div class=\"item\">" +
        "       <span class=\"item-name\"><strong>"+form.name.value+"</strong></span>\n" +
        "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc.value+"</p></span>\n" +
        "       <span class=\"item-price\">$"+ form.price.value +"</span>\n" +
        "       <span class=\"item-quantity\">"+ form.quant.value +"</span>\n" +
        "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
        "       <span class=\"item-hide-icon\"><p>hide</p></span>" +
        "   </div>"
        + items.innerHTML;

    resetAddItem()
}

function resetAddItem(){

    let addform = document.getElementById("addItem-form");
    addform.reset();
    addform.setAttribute("hidden", "hidden");

    activateAddButton()
    activateEditButtons()

}

function activateAddButton(){
    let button = document.getElementById("addItem");
    button.setAttribute("onclick", "addItem()")
}

function deactivateEditButtons(){
    let items = document.getElementsByClassName("item-edit-icon")
    for(var i=0;i<items.length;i++){
        items[i].removeEventListener("click", listener);
    }
}

function editItem(item){

    item.setAttribute("id", "editing")
    let inputs = item.getElementsByTagName("span");

    let name = inputs[0].innerText;
    let desc = inputs[1].innerText.slice(13,);
    let price = inputs[2].innerText.slice(1,);
    let quantity = inputs[3].innerText;

    item.innerHTML =
        "<form id='editItem-form' onsubmit='changeItemDetails(this);return false'>" +
        "    <span class=\"item-name\"><input type='text' name='name' placeholder='Enter item title' value='"+name+"' required></span>\n" +
        "    <span class=\"item-desc\"><textarea id='desc-changing' name='change-description' placeholder='Enter Description' cols='40' rows='6' required>"+desc+"</textarea></span>\n" +
        "    <span class=\"item-price\">$<input type='text' name='price' value='"+price+"' required></span>\n" +
        "    <span class=\"item-quantity\"><input type='text' name='quant' value='"+quantity+"' required></span>\n" +
        "    <span id='confirm-change' class=\"item-change-icon\"><button type=\"submit\">&#10003</button></span>\n" +
        "    <span id='delete-change' class=\"item-delete-icon\" onclick=\"undoChange('"+name+"','"+desc+"',"+price+","+quantity+")\">cancel</span>" +
        "</form>"



    let addItemButton = document.getElementById("addItem");
    addItemButton.removeAttribute("onclick");

    deactivateEditButtons()

}


function changeItemDetails(form){

    let item = form.parentNode;
    let desc = document.getElementById("desc-changing");
    item.innerHTML =
        "       <span class=\"item-name\"><strong>"+form.name.value+"</strong></span>\n" +
        "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc.value+"</p></span>\n" +
        "       <span class=\"item-price\">$"+ form.price.value +"</span>\n" +
        "       <span class=\"item-quantity\">"+ form.quant.value +"</span>\n" +
        "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
        "       <span class=\"item-hide-icon\"><p>hide</p></span>"

    activateEditButtons()
    activateAddButton()
}

function undoChange(name, desc, price, quantity){

    let item = document.getElementById("editing")
    item.innerHTML =
        "       <span class=\"item-name\"><strong>"+name+"</strong></span>\n" +
        "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc+"</p></span>\n" +
        "       <span class=\"item-price\">$"+ price +"</span>\n" +
        "       <span class=\"item-quantity\">"+ quantity +"</span>\n" +
        "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
        "       <span class=\"item-hide-icon\"><p>hide</p></span>"

    activateEditButtons()
    activateAddButton()

    item.removeAttribute("id")

}