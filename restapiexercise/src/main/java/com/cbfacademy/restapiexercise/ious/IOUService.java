package com.cbfacademy.restapiexercise.ious;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
@Service //- The @Service annotation marks the IOUService class as a service bean.
public class IOUService {
  @Autowired
  private final IOURepository iouRepository; // IOURepository as a dependency. The IOUService class is injected with an instance of IOURepository to interact with the database
// IOU service class accepts iouRepository as a dependency through the constructor (constructor injection)
  public IOUService(IOURepository iouRepository) {
    this.iouRepository = iouRepository;
  }
  // a. List<IOU> getAllIOUs()
  public List<IOU> getAllIOUs() {
    return this.iouRepository.findAll();
  }
  // copilot - added 12 8 2024.
  public IOU getIOU(UUID id) throws NoSuchElementException {
    return iouRepository.findById(id).orElseThrow(() -> new NoSuchElementException("IOU not found"));
  }
// from copilot - added 12/8/2024 -
  public IOU createIOU(IOU iou) throws IllegalArgumentException, OptimisticLockingFailureException {
    if (iou == null) {
        throw new IllegalArgumentException("IOU cannot be null");
    }
    return iouRepository.save(iou);
  }
  // d. IOU updateIOU(UUID id, IOU updatedIOU) throws NoSuchElementException
  public IOU updateIOU(UUID id, IOU updatedIOU) throws NoSuchElementException {
    IOU existingIOU = getIOU(id);
    existingIOU.setLender(updatedIOU.getLender());
    existingIOU.setAmount(updatedIOU.getAmount());
    existingIOU.setBorrower(updatedIOU.getBorrower());
    existingIOU.setDateTime(updatedIOU.getDateTime());
    return iouRepository.save(existingIOU);
  }
    // FROM tutor Andrew
  public void deleteIOU(UUID id) {
    this.iouRepository.delete(getIOU(id));
  }
// added 12/8/2024
  public List<IOU> getIOUsByBorrower(String borrower) {
    return iouRepository.findByBorrower(borrower);
  }
}