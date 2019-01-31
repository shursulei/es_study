package com.shursulei.es;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shursulei.es.model.Employee;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 *@Author:shursulei
 *@Description: 增删改查
 *@Date:14:30 2019/1/31
 *@Param:
 *@Return:
 */
public class EmployeeCRUDApp {
    @SuppressWarnings({ "unchecked", "resource" })
    public static void main(String[] args) throws Exception {
        // 先构建client
        Settings settings = Settings.builder()
                .put("cluster.name", "sulei")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

//		 createEmployee(client);
//       createEmployeeByJson(client);//创建index通过原声手动的方式json
//       createEmployeeByMap(client);//创建index通过map的方式
//        createEmployeeByjackson(client);//创建index通过jackson方式
//        updateEmployeeByUpdateRequest(client);
   	      getEmployee(client);
//		 updateEmployee(client);
//		 deleteEmployee(client);

        client.close();
    }

    /**
     *@Author:shursulei
     *@Description: index的实现通过手动原声json格式
     *@Date:14:46 2019/1/31
     *@Param:
     *@Return:
     */
    private static void createEmployeeByJson(TransportClient client) {

        String json = "{" +
                "\"name\":\"fendo\"," +
                "\"age\":\"27\"," +
                "\"position\":\"technique\"," +
                "\"join_date\":\"2017-01-01\"," +
                "\"salary\":\"20000\"" +
                "}";
        IndexResponse response = client.prepareIndex("company", "employee","3")
                        .setSource(json)
                        .get();
        System.out.println(response.getResult());
    }
    /**
     *@Author:shursulei
     *@Description: 创建index方式通过map的方式
     *@Date:14:57 2019/1/31
     *@Param:
     *@Return:
     */
    private static void createEmployeeByMap(TransportClient client){
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("name","lisi");
        json.put("age","28");
        json.put("position","tchnique");
        json.put("join_date","2018-01-01");
        json.put("salary","30000");
        IndexResponse response = client.prepareIndex("company", "employee","4")
                        .setSource(json)
                        .get();
        System.out.println(response.getResult());
    }
    /**
     *@Author:shursulei
     *@Description: 创建index通过jackson的格式
     *@Date:15:01 2019/1/31
     *@Param:
     *@Return:
     */
    private static void createEmployeeByjackson(TransportClient client) throws JsonProcessingException {
        Employee employee=new Employee();
        employee.setName("王五");
        employee.setAge("20");
        employee.setJoindate("technique");
        employee.setPosition("austaily");
        employee.setSalary(20000.00);
        // instance a json mapper
        ObjectMapper mapper = new ObjectMapper();// create once, reuse
        // generate json
        byte[] json =mapper.writeValueAsBytes(employee);
        IndexResponse response = client.prepareIndex("company", "employee","11")
                .setSource(json)
                .get();
        System.out.println(response.getResult());
    }
    /**
     * 创建员工信息（创建一个document）
     * @param client
     */
    private static void createEmployee(TransportClient client) throws Exception {
        IndexResponse response = client.prepareIndex("company", "employee", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", "jack")
                        .field("age", 27)
                        .field("position", "technique")
                        .field("country", "china")
                        .field("join_date", "2017-01-01")
                        .field("salary", 10000)
                        .endObject())
                .get();
        System.out.println(response.getResult());
    }

    /**
     * 获取员工信息
     * @param client
     * @throws Exception
     */
    private static void getEmployee(TransportClient client) throws Exception {
        GetResponse response = client.prepareGet("company", "employee", "1").setOperationThreaded(true).get();
        System.out.println(response.getSourceAsString());
    }

    /**
     * 修改员工信息
     * @param client
     * @throws Exception
     */
    private static void updateEmployee(TransportClient client) throws Exception {
        UpdateResponse response = client.prepareUpdate("company", "employee", "1")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("position", "technique manager")
                        .endObject())
                .get();
        System.out.println(response.getResult());
    }
    /**
     *@Description: 修改员工信息通过updateRequest
     *@Date:15:47 2019/1/31
     *@Param:
     *@Return:
     */
    private static void updateEmployeeByUpdateRequest(TransportClient client) throws IOException, ExecutionException, InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("company");
        updateRequest.type("employee");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder().startObject().field("name","jack2").endObject());
        client.update(updateRequest).get();
    }
    /**
     * 删除 员工信息
     * @param client
     * @throws Exception
     */
    private static void deleteEmployee(TransportClient client) throws Exception {
        DeleteResponse response = client.prepareDelete("company", "employee", "1").get();
        System.out.println(response.getResult());
    }
}
