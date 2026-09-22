import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Registro() {
  const navigate = useNavigate();

  const [usuario, setUsuario] = useState({
    nombre: "",
    apellido: "",
    correo: "",
    password: "",
    telefono: "",
    direccion: "",
    rol: "CLIENTE"
  });

  const cambiarValor = (e) => {
    setUsuario({
      ...usuario,
      [e.target.name]: e.target.value
    });
  };

  const registrar = async (e) => {
    e.preventDefault();

    const respuesta = await fetch("http://localhost:8080/api/usuarios", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(usuario)
    });

    if (respuesta.ok) {
      alert("Usuario registrado correctamente");
      navigate("/login");
    } else {
      alert("Error al registrar");
    }
  };

  return (
    <div style={{ maxWidth: "500px", margin: "40px auto" }}>

      <h2>Registro</h2>

      <form onSubmit={registrar}>

        <input
          type="text"
          name="nombre"
          placeholder="Nombre"
          onChange={cambiarValor}
        />

        <br /><br />

        <input
          type="text"
          name="apellido"
          placeholder="Apellido"
          onChange={cambiarValor}
        />

        <br /><br />

        <input
          type="email"
          name="correo"
          placeholder="Correo"
          onChange={cambiarValor}
        />

        <br /><br />

        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          onChange={cambiarValor}
        />

        <br /><br />

        <input
          type="text"
          name="telefono"
          placeholder="Teléfono"
          onChange={cambiarValor}
        />

        <br /><br />

        <input
          type="text"
          name="direccion"
          placeholder="Dirección"
          onChange={cambiarValor}
        />

        <br /><br />

        <button type="submit">
          Registrarse
        </button>

      </form>

    </div>
  );
}

export default Registro;