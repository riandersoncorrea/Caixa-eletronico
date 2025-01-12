package testecaixaeletronico;

import gestaodecaixaeletronico.CadastroContas;
import gestaodecaixaeletronico.Terminal;
import gestaodecontas.Cliente;
import gestaodecontas.Conta;


public class TesteCaixaEletronico {
    public static void main(String[] args) {
        CadastroContas cadastro = new CadastroContas(2);
        Terminal terminal = new Terminal(cadastro);

        Cliente cliente1 = new Cliente("Maria", "122");
        Cliente cliente2 = new Cliente("João", "123");

        Conta conta1 = new Conta(123, cliente1, 321, 200.0);
        Conta conta2 = new Conta(124, cliente2, 322, 300.0);
        

        cadastro.adicionaConta(conta1);
        cadastro.adicionaConta(conta2);
        terminal.iniciaOperacao();
    }
}