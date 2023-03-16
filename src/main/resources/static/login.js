function login() {
    let email=document.getElementById("login-email").value
    let password=document.getElementById("login-password").value
    let errorDiv=document.getElementById("login-error")
    $.ajax({
        method: "POST",
        url: `/login`,
        contentType: "application/json",
        data: JSON.stringify({"email": email, "password": password}),
        success: function (response) {
            window.sessionStorage.setItem("customerId", response.customerId)
            window.sessionStorage.setItem("token", response.token)
            document.cookie=`customerid=${response.customerId}`
            location.href=response.redirectUrl
        },
        error: function () {
            errorDiv.innerText="incorrect email or password, please try again"
        }
    })
}

function register() {
    let email=document.getElementById("register-email").value
    let password=document.getElementById("register-password").value
    let confirmPassword = document.getElementById("register-confirm-password").value
    let over18 = document.getElementById("register-checkbox").checked
    let errorDiv=document.getElementById("register-error")

    if (password === confirmPassword) {
        if (over18===true) {
            errorDiv.innerText=""
            $.ajax({
                method: "POST",
                url: `/register`,
                contentType: "application/json",
                data: JSON.stringify({"email": email, "password": password}),
                success: function (response) {
                    window.sessionStorage.setItem("customerId", response.customerId)
                    window.sessionStorage.setItem("token", response.token)
                    document.cookie=`customerid=${response.customerId}`
                    location.href=response.redirectUrl
                },
                error: function () {
                    errorDiv.innerText="user is registered, please try different email!"
                }
            })
        } else {
            errorDiv.innerText="registration is not allowed for customers under 18!"
        }
    } else {
        errorDiv.innerText="password doesn't match!"
    }

}
