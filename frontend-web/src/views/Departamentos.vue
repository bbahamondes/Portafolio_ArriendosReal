<template>
    <table id='dtDeptos'>
        <thead>
            <tr>
                <th>id</th>
                <th>Nombre</th>
                <th>Direccion</th>
                <th>Disponible</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="depto in deptos">
                <td v-bind:"depto"></td>
                <td v-bind:"depto"></td>
                <td v-bind:"depto"></td>
                <td v-bind:"depto"></td>
            </tr>
        </tbody>
    </table>
</template>
<script>
import Vue from 'vue'
new Vue({
    el: '#dtDeptos',
    data: {
        deptos: null
    },
    methods: {
        populateTable() {
            var settings = {
                "url": "http://40.117.177.9:8080/api/v1/departamento/all",
                "method": "GET",
                "timeout": 0,
                "processData": false,
                "mimeType": "multipart/form-data",
                "contentType": false
            };

            $.ajax(settings).done(function (response) {
                var deptos = JSON.parse(response);
                console.log(deptos);
            });

        }
    },
    mounted: function() {
        $('#dtDeptos').DataTable();
        this.populateTable()
    }
})
var $ = window.$ = window.jQuery = require( 'jquery' );
require( 'datatables.net-dt' );

export default {
    name: 'Departamentos',    
}


</script>
<style lang="sass" scoped>

</style>