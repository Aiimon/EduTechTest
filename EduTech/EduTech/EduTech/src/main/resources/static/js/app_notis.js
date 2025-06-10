function agregarNotificacion(mensaje) {
  const notificaciones = JSON.parse(localStorage.getItem("notificaciones") || "[]");
  const fecha = new Date().toLocaleString();
  notificaciones.unshift({ mensaje, fecha });
  localStorage.setItem("notificaciones", JSON.stringify(notificaciones));
  actualizarContadorNotificaciones();
}

function actualizarContadorNotificaciones() {
  const notificaciones = JSON.parse(localStorage.getItem("notificaciones")) || [];
  const contador = document.getElementById("contadorNotificaciones");

  // Protección para evitar error si el contador no existe
  if (!contador) return;

  contador.textContent = notificaciones.length;
  contador.style.display = notificaciones.length > 0 ? "inline-block" : "none";
}

function mostrarNotificaciones() {
  const dropdown = document.getElementById("notificacionesDropdown");
  const lista = document.getElementById("listaNotificaciones");

  if (!dropdown || !lista) return;

  dropdown.classList.toggle("d-none");
  lista.innerHTML = "";

  const notificaciones = JSON.parse(localStorage.getItem("notificaciones")) || [];

  if (notificaciones.length === 0) {
    lista.innerHTML = "<li class='text-muted'>No hay notificaciones nuevas.</li>";
  } else {
    notificaciones.forEach((noti) => {
      const li = document.createElement("li");
      li.classList.add("mb-2", "border-bottom", "pb-2");
      li.innerHTML = `<strong>${noti.mensaje}</strong><br><small class="text-muted">${noti.fecha}</small>`;
      lista.appendChild(li);
    });
  }

  actualizarContadorNotificaciones();
}

document.addEventListener("DOMContentLoaded", () => {
  actualizarContadorNotificaciones();
});

