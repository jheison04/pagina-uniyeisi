import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/login.css";

function Login() {

    const navigate = useNavigate();

    const [correo, setCorreo] = useState("");
    const [contraseña, setContraseña] = useState("");

    const iniciarSesion = async (e) => {

        e.preventDefault();

        const respuesta = await fetch(
            "http://localhost:8080/api/usuarios/login",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    correo,
                    password: contraseña
                })
            }
        );

        if (!respuesta.ok) {
            alert("Correo o contraseña incorrectos");
            return;
        }

        const usuario = await respuesta.json();

        localStorage.setItem("token", usuario.token);
        localStorage.setItem("nombre", usuario.nombre);
        localStorage.setItem("rol", usuario.rol);

        alert("Bienvenido " + usuario.nombre);

        navigate("/");
    };

    return (

        <div className="login">

            <h1>Iniciar Sesión</h1>

            <form onSubmit={iniciarSesion}>

                <input
                    type="email"
                    placeholder="Correo"
                    value={correo}
                    onChange={(e) => setCorreo(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Contraseña"
                    value={contraseña}
                    onChange={(e) => setContraseña(e.target.value)}
                />

                <button type="submit">
                    Entrar
                </button>

            </form>

        </div>

    );
}

export default Login;