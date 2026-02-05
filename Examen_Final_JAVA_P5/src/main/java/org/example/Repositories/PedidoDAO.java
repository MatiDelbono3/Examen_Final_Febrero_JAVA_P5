package org.example.Repositories;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

import org.example.Connections.HibernateUtils;
import org.example.DTO.DTOReportePedidos;
import org.example.Entities.Pedido;
import org.hibernate.Session;




public class PedidoDAO {
    public void CrearPedido(Pedido pedido) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
            session.save(pedido);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Pedido> ObtenerInscripciones(int idCliente) {
        try (Session session = HibernateUtils.getSession()) {
            CriteriaBuilder builder =  session.getCriteriaBuilder();
            CriteriaQuery<Pedido> criteria = builder.createQuery(Pedido.class);
            Root<Pedido> root = criteria.from(Pedido.class);
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (idCliente !=0 ){
                predicates.add(builder.equal(root.get("idAlumno"), idCliente));
                criteria.select(root);

            }
            return session.createQuery(criteria).getResultList();

        }
    }
    public void ActualizarEstadoPedido(Integer idPedido, String estado) {
        try (Session session = HibernateUtils.getSession()) {
            session.beginTransaction();
             Pedido pedido= session.get(Pedido.class, idPedido);
            if (pedido != null) {
                pedido.setEstado(estado);
                session.update(pedido);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Double>
    obtenerTotalPorCliente() {

        try (Session session = HibernateUtils.getSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Double> cq =
                    builder.createQuery(Double.class);

            Root<Pedido> pedido = cq.from(Pedido.class);
            Expression<Double> suma = builder.sum(pedido.get("montoTotal"));

            cq.select(suma)
                            .where(
                    builder.and(
                            builder.equal(pedido.get("estado"), "PAGADO")

                    )

            );

            cq.groupBy(pedido.get("cliente").get("idCliente"));
            return session.createQuery(cq).getResultList();
        }

    }
    public List<DTOReportePedidos.EstadisticaEstado>
    obtenerEstadisticaPorEstado() {

        try (Session session = HibernateUtils.getSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DTOReportePedidos.EstadisticaEstado> cq =
                    builder.createQuery(DTOReportePedidos.EstadisticaEstado.class);

            Root<Pedido> root = cq.from(Pedido.class);

            cq.select(
                    builder.construct(
                            DTOReportePedidos.EstadisticaEstado.class,
                            root.get("estado"),
                            builder.count(root)
                    )
            );

            cq.groupBy(root.get("estado"));
            return session.createQuery(cq).getResultList();
        }
    }
}

