package com.example.PracticaWeb.Enumerated;


import java.util.List;

public enum Role {
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_USER("ROLE_USER");

        private final String rol;

        Role(String string) {
                this.rol = string;
        }

        @Override
        public String toString(){
            return this.rol;
        }
}
