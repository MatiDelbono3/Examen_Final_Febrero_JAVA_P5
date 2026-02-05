package org.example.DTO;
import java.util.List;

public class DTOReportePedidos {
    private List<EstadisticaCliente> TotalPorCliente;
    private List<EstadisticaEstado> pedidosPorEstado;

    public DTOReportePedidos(List<Double> TotalPorCliente, List<EstadisticaEstado> pedidosPorEstado) {
        this.TotalPorCliente = TotalPorCliente;
        this.pedidosPorEstado=pedidosPorEstado;
    }

    public List<EstadisticaCliente> getTotalPorCliente() {
        return TotalPorCliente;
    }

    public void setTotalPorCliente(List<EstadisticaCliente> TotalPorCliente) {
        this.TotalPorCliente= TotalPorCliente;
    }

    public List<EstadisticaEstado> getPedidosPorPorEstado() {
        return pedidosPorEstado;
    }

    public void setPedidosPorEstado(List<EstadisticaEstado> pedidosPorEstado) {
        this.pedidosPorEstado = pedidosPorEstado;
    }

    public static class EstadisticaCliente {
        private int idCliente;
        private long Total;

        public EstadisticaCliente(int idCliente, long Total) {
            this.idCliente = idCliente;
            this.Total = Total;
        }

        public int getidCliente() {
            return idCliente;
        }

        public void setIdCliente(int idAlumno) {
            this.idCliente = idCliente;
        }

        public long getTotal() {
            return Total;
        }

        public void setCantidad(long cantidad) {
            this.Total = Total;
        }
    }

    public static class EstadisticaEstado {
        private String estado;
        private long cantidad;

        public EstadisticaEstado(String estado, long cantidad) {
            this.estado = estado;
            this.cantidad = cantidad;
        }
    }
}