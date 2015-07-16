package api

import (
	"fmt"
)

/**
 * If the main package does not import a module,
 * that module will not be initialized.
 * I created this function so that all api services will initialize
 */
func Register() {
	fmt.Println("Registered API!")
}
