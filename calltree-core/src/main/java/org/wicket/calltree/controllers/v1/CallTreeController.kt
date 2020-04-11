package org.wicket.calltree.controllers.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Alessandro Arosio - 11/04/2020 13:14
 */
@RestController
@RequestMapping("/api/v1/calltree")
class CallTreeController {

  @PostMapping
  fun initiateCalls() {
    // placeholder
  }

  @GetMapping
  fun getStats() {
    // placeholder
  }
}