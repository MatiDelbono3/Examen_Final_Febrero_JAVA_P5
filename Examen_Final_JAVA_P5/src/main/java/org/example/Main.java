package org.example;

import java.time.LocalDate;
import java.util.List;

import org.example.Entities.Cliente;
import org.example.Entities.Pedido;

import org.example.Repositories.PedidoDAO;
import org.example.Services.PedidoService;


public class Main {

    public static void main(String[] args) {

        Cliente cliente=new Cliente("Juan Carlos Tosetto", "CarlitosTosetto@gmail.com", true);
        PedidoService pedidoService = new PedidoService(new PedidoDAO());


        try {
            Pedido nuevoPedido = new Pedido( cliente,   LocalDate.of(2025, 12, 3), 145000.0, "PAGADO");
            pedidoService.CrearPedido(nuevoPedido);
            System.out.println("Pedido creado con éxito");

        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }


        try {
            System.out.println("Listado de pedidos:");

            // Listar pedidos
            List<Pedido> pedidos =
                    pedidoService.ObtenerPedidos(cliente.getIdCliente());

            pedidos.forEach(i ->
                    System.out.println(i.getFechaPedido() + " - " + i.getCliente())
            );


        } catch (Exception e) {
            System.out.println("Error al obtener pedidos: " + e.getMessage());
        }


        try {
            pedidoService.ActualizarEstado(1, "pendiente");
            System.out.println("Estado actualizado con éxito");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
        }

}
}