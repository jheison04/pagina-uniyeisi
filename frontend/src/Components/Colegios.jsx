import normal from "../assets/images/colegios/normal.jpg";
import carvajal from "../assets/images/colegios/carvajal.jpg";

function Colegios() {
  return (
   <section>
  <h2>Nuestros colegios</h2>

  <div className="colegios-container">

    <div className="colegio-card">
      <img src={normal} alt="Normal Superior de Medellín" />

      <h3>I.E. Normal Superior de Medellín</h3>

      <button>Ver productos</button>
    </div>

    <div className="colegio-card">
      <img src={carvajal} alt="Juan de Dios Carvajal" />

      <h3>I.E. Juan de Dios Carvajal</h3>

      <button>Ver productos</button>
    </div>

  </div>
</section>
  );
}

export default Colegios;