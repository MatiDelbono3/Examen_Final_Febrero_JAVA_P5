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
    public List<Pedido> ObtenerPedidos(int idCliente) {
        try (Session session = HibernateUtils.getSession()) {
            CriteriaBuilder builder =  session.getCriteriaBuilder();
            CriteriaQuery<Pedido> criteria = builder.createQuery(Pedido.class);
            Root<Pedido> root = criteria.from(Pedido.class);


            if (idCliente !=0 ){
                criteria.select(root)
                        .where(
                            builder.equal(
                                    root.get("cliente").get("idCliente"), idCliente
                            )
                        )
            ;


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
    public List<DTOReportePedidos.EstadisticaCliente>
    obtenerTotalPorCliente() {

        try (Session session = HibernateUtils.getSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<DTOReportePedidos.EstadisticaCliente> cq =
                    builder.createQuery(DTOReportePedidos.EstadisticaCliente.class);

            Root<Pedido> pedido = cq.from(Pedido.class);

            cq.select(
                            builder.construct(
                                    DTOReportePedidos.EstadisticaCliente.class,
                                    pedido.get("cliente").get("idCliente"),
                                    builder.sum(pedido.get("montoTotal"))
                            )
                    )
                    .where(
                            builder.equal(pedido.get("estado"), "PAGADO")
                    )
                    .groupBy(pedido.get("cliente").get("idCliente"));

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

