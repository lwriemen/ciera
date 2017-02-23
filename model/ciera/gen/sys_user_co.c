/*----------------------------------------------------------------------------
 * File:  sys_user_co.c
 *
 * Description:
 * Interface call-outs allow the user to capture execution control of the
 * generated system running on a target.
 * Especially in the deeply embedded software/hardware development world,
 * it may be necessary to tightly interface the xtUML system to the
 * surrounding/containing system. MC-3020 provides callout routines
 * that enable the user to easily interface code generated by the model
 * compiler with other system code. These callout routines are empty when
 * generated by the model compiler. It is up to the user to define
 * additional functionality (if necessary) to be performed at these
 * callout points.
 *--------------------------------------------------------------------------*/

#include "ciera_sys_types.h"
#include "sys_xtumlload.h"
#include "sys_user_co.h"

#ifdef SYS_USER_CO_PRINTF_ON
#include <stdio.h>
#define SYS_USER_CO_PRINTF( s ) printf( s );
#else
#define SYS_USER_CO_PRINTF( s )
#endif

/*
 * UserInitializationCallout
 *
 * This function is invoked at the immediate beginning of application
 * initialization. It is the very first function to be executed at system
 * startup.
 * User supplied implementation of this function should be restricted to
 * things like memory initialization, early hardware duties, etc.
 */
void
UserInitializationCalloutf( void )
{
/* Activate this invocation to initialize the example simple TIM.  */
  #if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  TIM_init();
  #endif
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserInitializationCallout\n" )
}

/*
 * UserPreOoaInitializationCallout
 *
 * This function is invoked immediately prior to executing any xtUML
 * initialization functions.
 */
void
UserPreOoaInitializationCalloutf( void )
{
  static char * a[2] = { "UserPreOoaInitializationCalloutf", "a.xtuml" };
  Escher_xtUML_load( 2, a );
}

/*
 * UserPostOoaInitializationCallout
 *
 * This function is invoked immediately after executing any xtUML
 * initialization functions.
 * When this callout function returns, the system dispatcher will allow the
 * xtUML application analysis state models to start consuming events.
 */
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>
extern int LOG_EnableTracing;
void
UserPostOoaInitializationCalloutf( int argc, char ** argv )
{
  {
    int c;
    opterr = 0;
    while ( ( c = getopt ( argc, argv, "i:t" ) ) != -1 ) {
      switch ( c ) {
        case 'i':
          if ( !optarg ) abort();
          else ciera_CIERA_op_setprojectroot( optarg );
          break;
        case 't':
          // enable tracing from the very beginning (even before marks are loaded)
          LOG_EnableTracing = 1;
          break;
        default:
          abort (); // die ignominiously
      }
    }
  }

  // translate the model
  ciera_translate();
}

/*
 * UserBackgroundProcessingCallout
 *
 * This function is invoked once during each loop execution of the system
 * dispather.
 * It is invoked at the 'top' of the system dispatcher loop, immediately
 * prior to dispatching any xtUML application analysis events.
 */
void
UserBackgroundProcessingCalloutf( void )
{
  /* Activate this invocation to periodically tick the example simple TIM.  */
  #if ESCHER_SYS_MAX_XTUML_TIMERS > 0
  TIM_tick();
  #endif
  /* Insert implementation specific code here.  */
  exit(0);
}

/*
 * UserPreShutdownCallout
 *
 * This function is invoked at termination of the system dispatcher, but
 * prior to performing any xtUML application analysis shutdown sequencing.
 */
void
UserPreShutdownCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPreShutdownCallout\n" )
}

/*
 * UserPostShutdownCallout
 *
 * This function is invoked immediately before application exit.
 */
void
UserPostShutdownCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPostShutdownCallout\n" )
}

/*
 * UserEventCantHappenCallout
 *
 * This function is invoked any time that an event is received that
 * results in a "cant happen" transition.
 */
void
UserEventCantHappenCalloutf(
  const Escher_StateNumber_t current_state,
  const Escher_StateNumber_t next_state,
  const Escher_EventNumber_t event_number )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventCantHappenCallout\n" )
}

/*
 * UserEventNoInstanceCallout
 *
 * This function is invoked when we failed to validate the instance
 * to which an event was directed.  This can happen in normal operation
 * when the instance was deleted while the event was in flight (analysis
 * error).
 */
void
UserEventNoInstanceCalloutf(
  const Escher_EventNumber_t event_number )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventNoInstanceCallout\n" )
}

/*
 * UserEventFreeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate an
 * event, but there are no more left.
 */
void
UserEventFreeListEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserEventFreeListEmptyCallout\n" )
}

/*
 * UserEmptyHandleDetectedCallout
 *
 * This function is invoked when an attempt is made to use an instance
 * reference variable (handle) that is null (empty).
 */
void
UserEmptyHandleDetectedCalloutf( c_t * object_keyletters, c_t * s )
{
  fprintf( stderr, "UserEmptyHandleDetectedCallout %s %s.\n", object_keyletters, s );
}

/*
 * UserObjectPoolEmptyCallout
 *
 * This function is invoked when an attempt is made to create an
 * instance of an object, but there are no instances available.
 */
void
UserObjectPoolEmptyCalloutf( const Escher_DomainNumber_t component_number, const Escher_ClassNumber_t class_number )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserObjectPoolEmptyCallout\n" )
}

/*
 * UserNodeListEmptyCallout
 *
 * This function is invoked when an attempt is made to allocate a
 * node, but there are no more left.
 */
void
UserNodeListEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserNodeListEmptyCallout\n" )
}

/*
 * UserInterleavedBridgeOverflowCallout
 *
 * This function is invoked when an attempt is made to post too many
 * interleaved bridges.  The depth of this list is defined by
 * SYS_MAX_INTERLEAVED_BRIDGES (unless changed in the archetype).
 */
void
UserInterleavedBridgeOverflowCalloutf( void )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserInterleavedBridgeOverflowCallout\n" )
}

/*
 * UserSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to self queue is
 * interrogated and found to be empty.
 */
void
UserSelfEventQueueEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
}

/*
 * UserNonSelfEventQueueEmptyCallout
 *
 * This function is invoked when the events to instance queue is
 * interrogated and found to be empty.
 */
void
UserNonSelfEventQueueEmptyCalloutf( void )
{
  /* Insert implementation specific code here.  */
}

/*
 * UserPersistenceErrorCallout
 *
 * This function is invoked when the events to instance queue is
 * interrogated and found to be empty.
 */
void
UserPersistenceErrorCalloutf( i_t error_code )
{
  /* Insert implementation specific code here.  */
  SYS_USER_CO_PRINTF( "UserPersistenceErrorCallout\n" )
}
