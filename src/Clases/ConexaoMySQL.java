
package Clases;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoMySQL {

    private static final String URL = "jdbc:mysql://localhost:3306/jeffley";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection conectar() {
        Connection conexao = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o banco de dados estabelecida.");

            // Chama a função para criar a tabela
            criarTabelaUsuario(conexao);
            criarTabelaPlanta(conexao);


        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            desconectar(conexao); // Fecha a conexão se ocorrer um erro
            conexao = null; // Define a conexão como nula em caso de erro
        }

        return conexao;
    }

    private static void criarTabelaUsuario(Connection conexao) {
        try {
            Statement stmt = conexao.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "nomeCompleto VARCHAR(255) NOT NULL,"
                    + "cpf VARCHAR(14) NOT NULL,"
                    + "senha VARCHAR(255) NOT NULL)";
            stmt.executeUpdate(sql);
            System.out.println("Tabela 'usuarios' criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }
    private static void criarTabelaPlanta(Connection conexao){
        try{
            Statement stmt = conexao.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS planta ("
                + "Data VARCHAR(255) PRIMARY KEY," 
                + "Nome VARCHAR(255) NOT NULL,"
                + "Linha VARCHAR(255) NOT NULL,"
                + "Planta VARCHAR(255) NOT NULL,"
                + "DadosBrix VARCHAR(255) NOT NULL,"
                + "DadosPressao VARCHAR(255) NOT NULL,"
                + "DadosMVerde VARCHAR(255) NOT NULL,"
                + "DadosMseca VARCHAR(255) NOT NULL)";
        stmt.executeUpdate(sql);
        System.out.println("Tabela 'Planta' criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela planta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conexao = conectar();

        if (conexao != null) {
            try {
                System.out.println("Conexao feita com sucesso !");

            } finally {
                // Certifique-se de fechar a conexão ao finalizar a aplicação
                desconectar(conexao);
            }
        }
    }

    private static void desconectar(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Conexão com o banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }
}
