import heroBanner from "../assets/images/hero-banner.png";
import "../styles/Hero.css";

function Hero() {
  return (
<section className="hero">

  <div className="hero-texto">

    <h1>Uniyeisi</h1>

    <p>
      Venta de uniformes escolares para instituciones educativas,
      con calidad, comodidad y atención personalizada.
    </p>

    <div className="hero-botones">
      <button>Ver catálogo</button>
      <button>Reservar uniforme</button>
      <button>Comprar ahora</button>
      <button>WhatsApp</button>
    </div>

  </div>

  <div className="hero-imagen">
    <img src={heroBanner} alt="Banner Uniyeisi" />
  </div>

</section>
  );
  
}

export default Hero;