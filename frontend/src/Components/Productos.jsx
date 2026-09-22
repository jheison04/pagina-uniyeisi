import { useEffect, useState } from "react";

function Productos() {

  const [productos, setProductos] = useState([]);

  useEffect(() => {

    fetch("http://localhost:8080/api/productos")
      .then((respuesta) => respuesta.json())
      .then((datos) => setProductos(datos))
      .catch((error) => console.error(error));

  }, []);

  return (
    <section className="productos">

      <h2>Productos Destacados</h2>

      <div className="productos-grid">

        {productos.map((producto) => (

          <div className="card-producto" key={producto.id}>

            <h3>{producto.nombre}</h3>

            <p>{producto.colegio}</p>

            <p>Talla: {producto.talla}</p>

            <p className="precio">
              ${producto.precio.toLocaleString("es-CO")}
            </p>

            <button>Ver producto</button>

          </div>

        ))}

      </div>

    </section>
  );
}

export default Productos;