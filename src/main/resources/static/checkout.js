function completeOrder() {
    let cardHolderName = document.getElementById("card-name").value
    let cardNum = document.getElementById("card-number").value
    let cardType = document.getElementById("card-type").value
    let date = document.getElementById("expiry-date").value
    let cvv = document.getElementById("cvv").value

    let requestBody = JSON.stringify({
        cardHolderName: cardHolderName,
        cardNum: cardNum,
        cardType: cardType,
        date: date,
        cvv: cvv
    })

    let customerId = (new URL(window.location)).searchParams.get("customerId")

    $.ajax({
        method: "POST",
        headers: {
            "customerId": `${sessionStorage.getItem("customerId")}`,
            "token": `${sessionStorage.getItem("token")}`
        },
        url: `/checkout?customerId=${customerId}`,
        contentType: "application/json; charset=utf-8",
        data: requestBody,
        complete: function(data) {
            document.write(data.responseText)
        }
    })
}