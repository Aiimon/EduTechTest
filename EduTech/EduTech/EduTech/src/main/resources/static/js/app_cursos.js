function precioConPun(precio) {
  return precio.toLocaleString('es-CL');
}

async function mostrarCursos() {
  try {
    const response = await fetch('/api/v1/cursos');
    if (!response.ok) throw new Error('No se pudieron cargar los cursos');
    const cursos = await response.json();

    const contenedor = document.getElementById('cursos-container');
    contenedor.innerHTML = '';

    cursos.forEach(curso => {
      const card = document.createElement('div');
      card.className = 'col-md-4 mb-4';

      card.innerHTML = `
        <div class="card h-100">
          <img src="${curso.imagen}" class="card-img-top" alt="${curso.nombre}" />
          <div class="card-body d-flex flex-column">
            <h5 class="card-title">${curso.nombre}</h5>
            <p class="card-text">${curso.descripcion}</p>
            <p class="card-text"><strong>Duración:</strong> ${curso.duracionCurso}</p>
            <p class="precio-curso">Precio: $${precioConPun(curso.precio)}</p>
            <p class="card-text">Cupos disponibles: ${curso.cupoDisponible}</p>
            <button class="btn btn-primary mt-auto"
              onclick="carrito.agregarCurso(${curso.id})"
              ${curso.cupoDisponible === 0 ? 'disabled' : ''}>
              ${curso.cupoDisponible === 0 ? 'Sin cupos' : 'Agregar al carrito'}
            </button>
          </div>
        </div>
      `;

      contenedor.appendChild(card);
    });
  } catch (error) {
    console.error('Error al cargar los cursos', error);
  }
  
}