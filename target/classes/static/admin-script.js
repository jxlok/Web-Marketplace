window.onload = function(){
    activateEditButtons()

}


let Editlistener = function editMode(){
    editItem(this.parentNode);
}

function activateEditButtons(){
    let items = document.getElementsByClassName("item-edit-icon")
    for(var i=0;i<items.length;i++){
        items[i].addEventListener("click", Editlistener);
    }
}

function deactivateEditButtons(){
    let items = document.getElementsByClassName("item-edit-icon")
    for(var i=0;i<items.length;i++){
        items[i].removeEventListener("click", Editlistener);
    }
}

function activateHideButtons(){
    let items = document.getElementsByClassName("item-hide-icon")
    for(var i=0;i<items.length;i++){
        items[i].setAttribute('aria-disabled', "true")
    }
}

function deactivateHideButtons(){
    let items = document.getElementsByClassName("item-hide-icon")
    for(var i=0;i<items.length;i++){
        items[i].removeAttribute('aria-disabled')
    }
}

// function activateUnhideButton(){
//     let items = document.getElementsByClassName("item-unhide-icon")
//     for(var i=0;i<items.length;i++){
//         items[i].removeEventListener("click", HideListener);
//     }
// }
//
// function analytics_tab(){
//     let analytics = document.getElementById('center-info');
//     let items = document.getElementById('sales-section');
//
//     analytics.removeAttribute("hidden");
//     items.setAttribute("hidden", "hidden");
// }
//
// function items_tab(){
//     let analytics = document.getElementById('center-info');
//     let items = document.getElementById('sales-section');
//
//     items.removeAttribute("hidden");
//     analytics.setAttribute("hidden", "hidden");
// }
//
// function displayItemForSale(){
//     let saleSection = document.getElementById('items-for-sale');
//     let hiddenSection = document.getElementById('hidden-items-container');
//
//     saleSection.removeAttribute("hidden");
//     hiddenSection.setAttribute("hidden", "hidden")
//
//     let saleTab = document.getElementById('sale-tab')
//     let hiddenTab = document.getElementById('hidden-tab')
//
//     saleTab.style.borderBottom = 0
//     hiddenTab.style.borderBottom = "1px solid silver"
// }
//
// function displayHiddenTab(){
//     let saleSection = document.getElementById('items-for-sale');
//     let hiddenSection = document.getElementById('hidden-items-container');
//
//     hiddenSection.removeAttribute("hidden");
//     saleSection.setAttribute("hidden", "hidden")
//
//     let saleTab = document.getElementById('sale-tab')
//     let hiddenTab = document.getElementById('hidden-tab')
//
//     hiddenTab.style.borderBottom = 0
//     saleTab.style.borderBottom = "1px solid silver"
//
// }

function addItem(){
    let form = document.getElementById("addItem-form");
    form.removeAttribute("hidden")

    let addItemButton = document.getElementById("addItem");
    addItemButton.removeAttribute("onclick");

    deactivateEditButtons()
}
//
// function addNewItem() {
//
//     let form = document.getElementById("addItem-form");
//     let items = document.getElementById("items");
//     let desc = document.getElementById("desc-new");
//     items.innerHTML =
//         "   <div class=\"item\">" +
//         "       <span class=\"item-name\"><strong>"+form.name.value+"</strong></span>\n" +
//         "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc.value+"</p></span>\n" +
//         "       <span class=\"item-price\">$"+ form.price.value +"</span>\n" +
//         "       <span class=\"item-quantity\">"+ form.quantity.value +"</span>\n" +
//         "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
//         "       <span class=\"item-hide-icon\"><p>hide</p></span>" +
//         "   </div>"
//         + items.innerHTML;
//
//     resetAddItem()
//
// }

function resetAddItem(){

    let addform = document.getElementById("addItem-form");
    addform.reset();
    addform.setAttribute("hidden", "hidden");

    activateAddButton()
    activateEditButtons()
    activateHideButtons();

}

function activateAddButton(){
    let button = document.getElementById("addItem");
    button.setAttribute("onclick", "addItem()")
}


function editItem(item){

    item.setAttribute("id", "editing")
    let inputs = item.getElementsByTagName("span");

    let name = inputs[0].innerText;
    let desc = inputs[1].innerText.slice(13,);
    let price = inputs[2].innerText.slice(1,);
    let quantity = inputs[3].innerText;
    var action

    if (document.title==="Manage items"){
        action = "@{manageItems}"
    }
    else{
        action = "@{hiddenItems}"
    }

    item.innerHTML =
        "<form id='editItem-form' method=\"POST\" th:action="+action+">" +
        "    <span class=\"item-name\"><input type='text' name='itemName' placeholder='Enter item title' value='"+name+"' readonly></span>\n" +
        "    <span class=\"item-desc\"><textarea id=\"desc-changing\" name=\"description\" placeholder='Enter Description' cols='40' rows='6' required>"+desc+"</textarea></span>\n"+
        "    <span class=\"item-price\">$<input type='number' step='any'  name='trainedPrice' value='"+price+"' required></span>\n" +
        "    <span class=\"item-quantity\"><input type='number' step='1' name='trainedStock' value='"+quantity+"' required></span>\n" +
        "    <span id='confirm-change' class=\"item-change-icon\"><button type=\"submit\">&#10003</button></span>\n" +
        "    <span id='delete-change' class=\"item-delete-icon\"><a onclick='location.reload()'>cancel</span>\n" +
        "</form>"



    let addItemButton = document.getElementById("addItem");
    addItemButton.removeAttribute("onclick");

    deactivateEditButtons()
    deactivateHideButtons()
}


// function changeItemDetails(form){
//
//     let item = form.parentNode;
//     let desc = document.getElementById("desc-changing");
//     item.innerHTML =
//         "       <span class=\"item-name\"><strong>"+form.name.value+"</strong></span>\n" +
//         "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc.value+"</p></span>\n" +
//         "       <span class=\"item-price\">$"+ form.price.value +"</span>\n" +
//         "       <span class=\"item-quantity\">"+ form.quantity.value +"</span>\n" +
//         "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
//         "       <span class=\"item-hide-icon\"><p>hide</p></span>"
//
//     activateEditButtons()
//     activateAddButton()
//     activateHideButtons()
// }
//
//
// function undoChange(name, desc, price, quantity){
//     location.reload()
//
//     //
//     // let item = document.getElementById("editing")
//     // item.removeAttribute("id")
//     //
//     // var hide
//     // var link
//     // if(document.title==="Manage Items"){
//     //     hide="hide"
//     // }
//     // else{
//     //     hide="unhide"
//     // }
//     // item.innerHTML =
//     //     "       <span class=\"item-name\"><strong>"+name+"</strong></span>\n" +
//     //     "       <span class=\"item-desc\"><p><strong>Description:</strong> "+desc+"</p></span>\n" +
//     //     "       <span class=\"item-price\">$"+ price +"</span>\n" +
//     //     "       <span class=\"item-quantity\">"+ quantity +"</span>\n" +
//     //     "       <span class=\"item-edit-icon\"><p>edit</p></span>\n" +
//     //     "       <span class=\"item-hide-icon\"><p><a th:href=\""+'@{manageItems}'+'/'+hide+'/'+'${item.getId}'+"\">"+hide+"</a></p></span>"
//
//     // document.getElementById("items").innerHTML=" "
//     // $("#items").load(' #items')
//     // activateEditButtons()
//     // activateHideButtons()
// }
//
// function hideItem(item){
//
//     let hiddenItems = document.getElementById('hidden-items');
//
//     let hideDiv = item.getElementsByClassName('item-hide-icon')[0];
//     hideDiv.className = "item-unhide-icon"
//     hideDiv.innerHTML="<p>unhide</p>";
//
//     hiddenItems.innerHTML = hiddenItems.innerHTML + "<div class='item'>" + item.innerHTML + "</div>";
//     item.parentNode.removeChild(item);
//
//     activateEditButtons()
//     activateHideButtons()
// }
//
// function unHideItem(item){
//
//     let items = document.getElementById('items')
//
//     let hideDiv = item.getElementsByClassName('item-unhide-icon')[0];
//     hideDiv.className = "item-hide-icon"
//     hideDiv.innerHTML="<p>hide</p>";
//
//     items.innerHTML = "<div class='item'"+item.innerHTML+"</div>" + items.innerHTML;
//     item.parentNode.removeChild(item);
//
//     activateEditButtons()
//     activateHideButtons()
//
// }