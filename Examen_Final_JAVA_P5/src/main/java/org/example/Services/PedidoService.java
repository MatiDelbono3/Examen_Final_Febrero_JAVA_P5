package org.example.Services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.example.DTO.DTOReportePedidos;
import org.example.Entities.Pedido;
import org.example.Repositories.PedidoDAO;

public class PedidoService {

    private PedidoDAO pedidoDAO;

    public PedidoService( PedidoDAO pedidoDAO)
    {
        this.pedidoDAO = pedidoDAO;
    }

    public void CrearPedido(Pedido pedido) {

        if (pedido.getFechaPedido() == null ) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        if (pedido.getEstado() == null || pedido.getEstado().isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        pedidoDAO.CrearPedido(pedido);
    }

    public List<Pedido> ObtenerPedidos (int idCliente) {
        return pedidoDAO.ObtenerPedidos(idCliente);
    }

    public void ActualizarEstado(Integer idPedido, String estado) {

        if (idPedido == null || idPedido <= 0) {
            throw new IllegalArgumentException("ID de pedido no válido");
        }


        if (estado != null && !estado.matches("Pendiente|Pagado|Cancelado")) {
            throw new IllegalArgumentException("El estado  debe ser 'Pendiente', 'Pagado' o 'Cancelado'");
        }
        pedidoDAO.ActualizarEstadoPedido(idPedido, estado);
    }

    public DTOReportePedidos generarReportePedidos() {

        CompletableFuture<List<DTOReportePedidos.EstadisticaCliente>> porCliente =
                CompletableFuture.supplyAsync(() -> pedidoDAO.obtenerTotalPorCliente());

        CompletableFuture<List<DTOReportePedidos.EstadisticaEstado>> porEstado =
                CompletableFuture.supplyAsync(() -> pedidoDAO.obtenerEstadisticaPorEstado());

        CompletableFuture.allOf(porCliente, porEstado).join();

        try {
            return new DTOReportePedidos(
                    porCliente.get(),
                    porEstado.get()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error generando reporte", e);
        }
    }
}
