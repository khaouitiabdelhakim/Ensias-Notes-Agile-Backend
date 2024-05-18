package com.ensias.ensiasnote.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Response payload for generic messages.
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
	private String message; // The message content

}
