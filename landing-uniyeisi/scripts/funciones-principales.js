


// 1. ESPERAR A QUE EL HTML ESTÉ COMPLETAMENTE CARGADO

document.addEventListener('DOMContentLoaded', function() {

    
    // 2. ESTADO DEL NEGOCIO (ABIERTO / CERRADO)
    
    function actualizarEstadoNegocio() {
        const ahora = new Date();
        const dia = ahora.getDay();       // 0=domingo, 1=lunes...
        const hora = ahora.getHours();
        const minutos = ahora.getMinutes();
        const horaActual = hora + minutos / 60;

        let abierto = false;

        // Horario del negocio
        const horario = {
            lunes: { apertura: 8, cierre: 18 },
            martes: { apertura: 8, cierre: 18 },
            miercoles: { apertura: 8, cierre: 18 },
            jueves: { apertura: 8, cierre: 18 },
            viernes: { apertura: 8, cierre: 18 },
            sabado: { apertura: 9, cierre: 13 }
        };

        const diasSemana = ['domingo', 'lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado'];
        const nombreDia = diasSemana[dia];

        // Verificar si está abierto
        if (nombreDia === 'domingo') {
            abierto = false;
        } else if (horario[nombreDia]) {
            const { apertura, cierre } = horario[nombreDia];
            abierto = (horaActual >= apertura && horaActual < cierre);
        }

        // Actualizar el indicador visual
        const indicador = document.querySelector('.indicador-estado');
        const textoEstado = document.querySelector('.estado-negocio span');

        if (indicador && textoEstado) {
            if (abierto) {
                indicador.className = 'indicador-estado abierto';
                textoEstado.textContent = '🟢 Abierto ahora';
            } else {
                indicador.className = 'indicador-estado cerrado';
                textoEstado.textContent = '🔴 Cerrado ahora';
            }
        }
    }

    // Ejecutar la función al cargar la página
    actualizarEstadoNegocio();

    
    // 3. SCROLL SUAVE PARA ENLACES INTERNOS
    
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const href = this.getAttribute('href');
            if (href === '#') return;

            e.preventDefault();
            const target = document.querySelector(href);
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    
    // 4. BOTÓN "VER CATÁLOGO" (HEADER)
    
    const botonCatalogo = document.querySelector('.boton-catalogo-header');
    if (botonCatalogo) {
        botonCatalogo.addEventListener('click', function() {
            const seccionCatalogo = document.querySelector('#seccion-catalogo');
            if (seccionCatalogo) {
                seccionCatalogo.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    }

    
    // 5. BOTONES DE WHATSAPP
    
    const numeroWhatsApp = '573127361788'; 

    document.querySelectorAll('.boton-whatsapp-header, .boton-whatsapp-banner, .boton-whatsapp-ubicacion').forEach(boton => {
        boton.addEventListener('click', function(e) {
            e.preventDefault();
            const mensaje = encodeURIComponent('Hola, estoy interesado en los uniformes de Uniyeisi');
            window.open(`https://wa.me/${numeroWhatsApp}?text=${mensaje}`, '_blank');
        });
    });

    
    // 6. MENSAJE EN CONSOLA (PARA SABER QUE FUNCIONA)
    
    console.log('🚀 Uniyeisi - Landing Page cargada correctamente');
    console.log('📅 Estado del negocio actualizado');

});