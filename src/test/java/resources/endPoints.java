package resources;

public enum endPoints {

    /*enum enables to create a method holding some parameter
       It will require constructor along with it
     */
    addPlaceAPI("maps/api/place/add/json"),
    getPlaceAPI("/maps/api/place/get/json"),
    deletePlaceAPI("maps/api/place/delete/json");
    private String endPoint;

    /*Below constructor will be invoked and the value from endPoint will come from step definition
    this.endPoint will return current endPoint (V104)
    value for endPoint from constructor will be assigned to private variable above and will be fetched
    from getEndPoint method
    */

    endPoints(String endPoint){
       this.endPoint=endPoint;
    }

    public String getEndPoint(){
        return endPoint;
    }



}
