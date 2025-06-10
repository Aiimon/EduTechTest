function registrar(){
    const nombre = document.getElementById("nombre").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    if(!nombre || !email || !password){
        alert("Por favor complete todos los campos.");
        return
    }

    fetch("http://localhost:8080/api/v1/usuarios/registrar", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({ nombre, email, password})
    })
    .then(response => response.json())
    .then(data =>{
        localStorage.setItem("usuarioLogueado", JSON.stringify({
            id: data.id,
            username: data.nombre,
            email: data.email
        }));
        alert("Usuario creado con exito!")
        window.location.href = "/index.html";
    });
}