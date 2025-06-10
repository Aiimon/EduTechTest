const nombre = localStorage.getItem("nombreUsuario");
    if (nombre){ ///si el campo existe, que lo muestre
        document.getElementById("mensaje").textContent = `Bienvenid@, ${nombre}`;
    }
    function cerrarSesion(){
        localStorage.clear();
        window.location.href = "login.html";
    }