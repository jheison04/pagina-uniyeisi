
import Hero from "../Components/Hero";
import Colegios from "../Components/Colegios";
import Pruductos from "../Components/Productos";
import PorqueElegirnos from "../Components/PorqueElegirnos";
import Contacto from "../Components/Contacto";
import QuienesSomos from "../Components/QuienesSomos";
import Footer from "../Components/Footer";

function Home() {
  return (
    <div>
      <Hero />
      <Colegios />
      <Pruductos />
      <PorqueElegirnos />
      <Contacto />
      <QuienesSomos />
      <Footer />
        
    </div>
  );
}

export default Home;