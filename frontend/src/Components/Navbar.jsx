function Navbar() {
  return (
    <nav className="navbar">
      <h2 className="logo">Uniyeisi</h2>

      <ul className="menu">
        <li><a href="#">Inicio</a></li>
        <li><a href="#">Colegios</a></li>
        <li><a href="#">Productos</a></li>
        <li><a href="#">Nosotros</a></li>
        <li><a href="#">Contacto</a></li>
        <li>
            <Link to="/login">Iniciar sesión</Link>
        </li>

        <li>
            <Link to="/registro">Registrarse</Link>
        </li>
      </ul>
    </nav>
  );
}

export default Navbar;

import { Link } from "react-router-dom";