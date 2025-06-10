const API_URL = "/api/v1/carrito";

function precioConPun(precio) {
  if (typeof precio !== "number") {
    return "0";
  }
  return precio.toLocaleString('es-CL');
}

const carrito = (() => {
  let cursosEnCarrito = [];

  async function listarCarrito() {
    const tbody = document.querySelector("#tablaCarrito tbody");
    try {
      const response = await fetch(API_URL);
      cursosEnCarrito = await response.json();

      let filasHTML = "";
      let total = 0;

      cursosEnCarrito.forEach(curso => {
        if (!curso || !curso.id || !curso.nombre || typeof curso.precio !== "number") {
          console.warn("Curso con datos inválidos:", curso);
          return;
        }
        total += curso.precio;
        filasHTML += `
          <tr>
            <td>${curso.id}</td>
            <td>${curso.nombre}</td>
            <td>$${precioConPun(curso.precio)}</td>
            <td>
              <button class="btn btn-danger btn-sm" onclick="carrito.eliminarCurso(${curso.id})">Eliminar</button>
            </td>
          </tr>`;
      });

      tbody.innerHTML = filasHTML;
      document.getElementById("totalCarrito").textContent = cursosEnCarrito.length;
      document.getElementById("totalPrecio").textContent = `$${precioConPun(total)}`;

    } catch (error) {
    }
  }

  async function agregarCurso(idCurso) {
    try {
      const resReducir = await fetch(`/api/v1/cursos/${idCurso}/reducir-cupo`, { method: "PUT" });
      if (!resReducir.ok) {
        alert("No hay cupos disponibles para este curso.");
        return;
      }
      
      const response = await fetch(`${API_URL}/agregar/${idCurso}`, { method: "POST" });
      if (!response.ok) throw new Error("No se pudo agregar curso");

      alert("Curso agregado al carrito");
      await listarCarrito();
    } catch (error) {
      console.error(error);
      alert("Error al agregar curso");
    }
  }

  async function eliminarCurso(idCurso) {
    try {
      const resRestaurar = await fetch(`/api/v1/cursos/${idCurso}/restaurar-cupo`, { method: "PUT" });
      if (!resRestaurar.ok) {
        alert("Error al restaurar cupo del curso.");
        return;
      }

      const response = await fetch(`${API_URL}/eliminar/${idCurso}`, { method: "DELETE" });
      if (!response.ok) throw new Error("No se pudo eliminar curso");

      alert("Curso eliminado");
      await listarCarrito();
    } catch (error) {
      console.error(error);
      alert("Error al eliminar curso");
    }
  }

  async function vaciarCarrito(sinConfirmacion = false) {
    if (sinConfirmacion || confirm("¿Seguro quieres vaciar el carrito?")) {
      try {
        for (const curso of cursosEnCarrito) {
          await fetch(`/api/v1/cursos/${curso.id}/restaurar-cupo`, { method: "PUT" });
        }

        const response = await fetch(`${API_URL}/vaciar`, { method: "DELETE" });
        if (!response.ok) throw new Error("No se pudo vaciar carrito");

        if (!sinConfirmacion) alert("Carrito vaciado");
        await listarCarrito();
      } catch (error) {
        console.error(error);
        alert("Error al vaciar carrito");
      }
    }
  }



async function confirmarCompra() {
  if (cursosEnCarrito.length === 0) {
    alert("El carrito está vacío");
    return;
  }

  const confirmar = confirm(`¿Confirmas la compra de ${cursosEnCarrito.length} curso(s)?`);
  if (!confirmar) return;

  try {
    const usuario = JSON.parse(localStorage.getItem("usuarioLogueado"));
    if (usuario) {
      const cursosInscritos = JSON.parse(localStorage.getItem("cursosInscritos")) || {};
      if (!cursosInscritos[usuario.username]) {
        cursosInscritos[usuario.username] = [];
      }

      cursosEnCarrito.forEach(curso => {
        if (!cursosInscritos[usuario.username].some(c => c.id === curso.id)) {
          cursosInscritos[usuario.username].push(curso);
        }
      });

      localStorage.setItem("cursosInscritos", JSON.stringify(cursosInscritos));
    }

    alert("Compra confirmada con éxito");
    agregarNotificacion(`Compra confirmada: ${cursosEnCarrito.length} curso(s) adquiridos.`);
    await vaciarCarrito(true);

  } catch (error) {
    console.error(error);
    alert("Error al confirmar compra");
  }
}


  return { listarCarrito, agregarCurso, eliminarCurso, vaciarCarrito, confirmarCompra };
})();