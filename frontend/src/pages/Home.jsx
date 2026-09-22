
import Hero from "../components/Hero";
import Colegios from "../components/Colegios";
import Productos from "../components/Productos";
import PorqueElegirnos from "../components/PorqueElegirnos";
import Contacto from "../components/Contacto";
import QuienesSomos from "../components/QuienesSomos";
import Footer from "../components/Footer";
import Navbar from "../components/Navbar";

function Home() {
  return (
    <div>
      <Navbar />
      <Hero />
      <Colegios />
      <Productos />
      <PorqueElegirnos />
      <Contacto />
      <QuienesSomos />
      <Footer />
        
    </div>
  );
}

export default Home;