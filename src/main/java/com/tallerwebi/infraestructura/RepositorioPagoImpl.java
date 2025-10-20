package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Pago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.interfaces.RepositorioPago;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }




    @Override
    public String obtenerPagoPorCollectionId(String collectionId) {

        final Session session = sessionFactory.getCurrentSession();


        Pago pago = (Pago) session.createCriteria(Pago.class)
                .add(Restrictions.eq("collectionId", collectionId))
                .uniqueResult();

        if (pago != null) {
            return pago.getCollectionId();
        }

        return null;
    }

    @Override
    public void registrarPago(Pago pago) {


        sessionFactory.getCurrentSession().save(pago);

    }


}
