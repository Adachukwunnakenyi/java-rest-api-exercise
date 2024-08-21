package com.cbfacademy.restapiexercise.ious;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/ious") //sets the base path for all endpoints in this controller. Any request URI handled by this class will start with "/api/books". Therefore, each method’s path is relative to this base path. This approach helps avoid repetition and makes the code cleaner and easier to maintain.
public class IOUController {
  // iouService class injected as a dependency
  @Autowired
  private final IOUService iouService;
  public IOUController(IOUService iouService) {
  this.iouService = iouService;
  }
  @GetMapping(produces = "application/json")
  public ResponseEntity<List<IOU>> getAllIOUs(@RequestParam(required = false) String borrower){
    if (borrower == null || borrower.isEmpty()) {
      List<IOU>ious=iouService.getAllIOUs();// call service method to getAllIous
        return ResponseEntity.ok(ious); //return 200 ok with iou
    }
    else {
          List<IOU> ious=iouService.getIOUsByBorrower(borrower); //call service method to filter by browser
          return ResponseEntity.ok(ious);
          }
    }
  // Retrieve a specific IOU by its ID
    @GetMapping(value="/{id}", produces = "application/json" )
    public ResponseEntity <IOU> getIOU(@PathVariable UUID id){
           try{
             IOU iou = iouService.getIOU(id);
             if(iou != null){
            return ResponseEntity.ok(iou); //return 200 ok with iou
        }else{
            return ResponseEntity.notFound().build(); //return 404 not found
        }
        } catch (NoSuchElementException e) {
          return ResponseEntity.notFound().build(); // Handle case where IOU is not found
        }
    }
  // added  @ResponseStatus(HttpStatus.CREATED)
  @ResponseStatus(HttpStatus.CREATED) // sets the HTTP status code to 201 CREATED for the response regardless of the return type.It eliminates the need to use ResponseEntity to set the status code manually.
  @PostMapping(produces = "application/json")
  public IOU createIou(@RequestBody IOU iou) {
    return iouService.createIOU(iou);
  }
  // Update an existing IOU by ID
  @PutMapping(value="/{id}", produces = "application/json")
  public ResponseEntity<IOU> updateIou(@PathVariable UUID id, @RequestBody IOU iou) {
      try{
      IOU updatedIou =iouService.updateIOU(id,iou);
        return ResponseEntity.ok(updatedIou); //return 200 ok with iou
      }catch (NoSuchElementException e) {
       return ResponseEntity.notFound().build();
      }
  }
  // Delete an IOU by ID
  @DeleteMapping(value="/{id}",produces ="application/json")
  public ResponseEntity <Void> deleteIou(@PathVariable UUID id) {
   try {
    iouService.deleteIOU(id);
      return ResponseEntity.ok().build(); //return 200 ok with iou
    } catch (NoSuchElementException e) {
       return ResponseEntity.notFound().build();
    }
  }
}
