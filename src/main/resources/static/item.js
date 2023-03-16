function addToCart(itemId) {
    let cartCount = document.getElementById("basket-count")
    $.ajax({
        method:'POST',
        headers: {
            "customerId": `${sessionStorage.getItem("customerId")}`,
            "token": `${sessionStorage.getItem("token")}`
        },
        url:`/cart/items/${itemId}`,
        success: function () {
            let currentCount = parseInt(cartCount.innerText)
            cartCount.innerText = `${currentCount + 1}`
        },
        error: function () {
            window.location.href="/login"
        }
    })
}