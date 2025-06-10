function login(){
    fetch("http://localhost:8080/api/v1/usuarios/login",{
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
        }),
    })
    .then(response => response.json())
    .then(data => {
        if(data.result === "OK"){
            localStorage.setItem("usuarioLogueado", JSON.stringify({
                id: data.id,
                username: data.nombre, 
                email: data.email
            }));
            window.location.href = "/index.html";
        }else {
            alert("El acceso ha sido denegado.");
        }
    });
}