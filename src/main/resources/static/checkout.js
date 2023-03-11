function completeOrder() {
    let cardHolderName = document.getElementById("card-name").innerText
    let cardNum = document.getElementById("card-number").innerText
    let cardType = document.getElementById("card-type").innerText
    let date = document.getElementById("expiry-date").innerText
    let cvv = document.getElementById("cvv").innerText

    let requestBody = JSON.stringify({
        cardHolderName: cardHolderName,
        cardNum: cardNum,
        cardType: cardType,
        date: date,
        cvv: cvv
    })

    let orderId = (new URL(window.location)).searchParams.get("orderId")

    $.ajax({
        method: "POST",
        url: `/checkout?orderId=${orderId}`,
        contentType: "application/json; charset=utf-8",
        body: requestBody,
        complete: function(data) {
            document.write(data.responseText)
        }
    })
}