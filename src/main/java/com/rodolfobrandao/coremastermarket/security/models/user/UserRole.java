package com.rodolfobrandao.coremastermarket.security.models.user;

    public enum UserRole {

        SUPERADMIN("superadmin"),
//        Tem controle total sobre todo o sistema.
//        Pode criar, editar, e deletar qualquer dado ou usuário.
//        Gerencia configurações avançadas do sistema.
        ADMIN("admin"),
//        Tem controle sobre a maioria dos aspectos do sistema, mas com algumas restrições em relação ao SUPERADMIN.
//        Gerencia usuários, permissões e configurações globais.
//        Pode visualizar todos os dados do sistema.
        MODERATOR("moderador"),
//        Tem permissões para moderar o conteúdo e gerenciar interações entre usuários.
//        Pode aprovar ou rejeitar registros, comentários, ou outros conteúdos gerados pelos usuários.
        USER("user"),
//        Tem acesso básico às funcionalidades do sistema.
//        Pode visualizar e editar apenas os próprios dados.
//        Pode realizar tarefas cotidianas relacionadas à sua função específica.

        TECHNICIAN("tecnico"),
//        Tem acesso a funcionalidades técnicas do sistema.
//        Pode visualizar e interagir com dados técnicos, resolver problemas técnicos, e realizar manutenção do sistema.
        CUSTOMER("cliente"),
//        Tem acesso a uma interface limitada e simplificada.
//        Pode visualizar e gerenciar apenas os próprios dados e interações.
//        Acesso restrito a funcionalidades essenciais, como verificação de status de pedidos ou visualização de faturas.
        FINANCE("financeiro"),
//        Acesso a todas as funcionalidades financeiras, como faturas, pagamentos e relatórios financeiros.
//        Pode gerar e visualizar relatórios financeiros detalhados.
        SALES("vendas"),
//        Acesso a funcionalidades de vendas, como gerenciamento de leads, oportunidades, e acompanhamento de clientes.
//        Pode gerar cotações, pedidos de venda e relatórios de vendas.
        HR("hr"),
//        Acesso a funcionalidades de recursos humanos, como gerenciamento de funcionários, folhas de pagamento e benefícios.
//        Pode visualizar e editar dados de funcionários e gerar relatórios de RH.
        INVENTORY("inventario");
//        Acesso a funcionalidades de gerenciamento de estoque, como controle de entradas e saídas, e monitoramento de níveis de estoque.
//        Pode gerar relatórios de inventário e realizar auditorias de estoque.


        private String role;

        UserRole(String role){
            this.role = role;
        }

        public String getRole(){
            return role;
        }
    }
