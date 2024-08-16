package service.GameService

import entity.Player
import service.GameService
import service.RootService
import kotlin.test.assertEquals
import kotlin.test.*
/**
 * Class that provides tests for [GameService].
 *
 *
 * @property rootService A [RootService] instance used to initialize the [GameService].
 * @property gameservice The [GameService] instance to be tested.
 */

class initialisePyramidTest {

        private val rootService = RootService()
        private val gameservice = GameService(rootService)


        /**
         * Tests the startNewGame function of [GameService].
         */
        private val cards =gameservice.createDeck()

    /**
     * Überprüfft ob die pyramide gut initialisiert ist
     */
        @Test
        fun testinitialisePyramid() {
            val pyramid =gameservice.initialisePyramid(cards)
            assertEquals(1,pyramid[6].size)
            assertEquals(2,pyramid[5].size)
            assertEquals(3,pyramid[4].size)
            assertEquals(4,pyramid[3].size)
            assertEquals(5,pyramid[2].size)
            assertEquals(6,pyramid[1].size)
            assertEquals(7,pyramid[0].size)
            assertEquals(7,pyramid.size)
            assertTrue { pyramid[0][0]!!.visible }
            assertFalse { pyramid[0][1]!!.visible }
            assertTrue { pyramid[0][6]!!.visible }
            assertTrue { pyramid[1][0]!!.visible }
            assertTrue { pyramid[1][5]!!.visible }
            assertTrue { pyramid[2][0]!!.visible }
            assertTrue { pyramid[2][4]!!.visible }
            assertTrue { pyramid[3][0]!!.visible }
            assertTrue { pyramid[3][3]!!.visible }
            assertTrue { pyramid[4][0]!!.visible }
            assertTrue { pyramid[4][2]!!.visible }
            assertTrue { pyramid[5][0]!!.visible }
            assertTrue { pyramid[5][1]!!.visible }
            assertTrue { pyramid[6][0]!!.visible }



        }
    }
